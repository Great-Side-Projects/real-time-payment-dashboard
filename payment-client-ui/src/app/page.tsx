'use client'

import {useState, useEffect} from 'react'
import TransactionForm from '../components/TransactionForm'
import NotificationList from '../components/NotificationList'
import KibanaDashboard from '../components/KibanaDashboard'
import {sendTransaction} from '../services/api'
import {initializeWebSocket} from '../services/websocket'
import {Notification, Transaction} from '../types'
import {Toaster} from "@/components/ui/toaster"
import {useToast} from "@/hooks/use-toast"

export default function Dashboard() {
    const [notifications, setNotifications] = useState<Notification[]>([])
    const {toast} = useToast()
    useEffect(() => {
        const closeWebSocket = initializeWebSocket((notification) => {
            setNotifications(prev => [notification, ...prev].slice(0, 5))
        })

        return closeWebSocket
    }, [])

    const handleTransactionSubmit = async (transaction: Transaction) => {
        try {
            await sendTransaction(transaction)
            toast({
                title: "Transaction Sent",
                description: "Transaction was sent successfully",
            })
        } catch (error) {
            console.error('Error:', error)
            toast({
                title: "Error",
                description: "Failed to send transaction",
                variant: "destructive",
            })
        }
    }


    return (
        <div className="container mx-auto p-4">
            <h1 className="text-3xl font-bold mb-6">Advanced Real-Time Payment Processing Dashboard</h1>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <TransactionForm onSubmit={handleTransactionSubmit}/>
                <NotificationList notifications={notifications}/>
            </div>

            <KibanaDashboard/>
            <Toaster/>
        </div>
    )
}