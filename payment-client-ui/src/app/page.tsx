'use client'

import { useState, useEffect } from 'react'
import TransactionForm from '../components/TransactionForm'
import NotificationList from '../components/NotificationList'
import KibanaDashboard from '../components/KibanaDashboard'
import { sendTransaction, getTransactions } from '../services/api'
import { initializeWebSocket } from '../services/websocket'
import { Notification, Transaction } from '../types'
import { Toaster } from "@/components/ui/toaster"
import { useToast } from "@/hooks/use-toast"
import TransactionConsole from '../components/TransactionConsole'
import TransactionFilter from '../components/TransactionFilter'
import TransactionTable from '../components/TransactionTable'
import {
    Accordion,
    AccordionContent,
    AccordionItem,
    AccordionTrigger,
  } from "@/components/ui/accordion"

export default function Dashboard() {
    const [notifications, setNotifications] = useState<Notification[]>([])
    const [consoleTransactions, setConsoleTransactions] = useState<Transaction[]>([])
    const { toast } = useToast()
    const [transactions, setTransactions] = useState<Transaction[]>([])
    const [currentPage, setCurrentPage] = useState(1)
    const [hasNext, setHasNext] = useState(false)
    const [nextPagingState, setNextPagingState] = useState<string | null>(null)
    const [filterParams, setFilterParams] = useState<{ [key: string]: string | number }>({})


    useEffect(() => {
        const closeWebSocket = initializeWebSocket((notification) => {
            setNotifications(prev => [notification, ...prev].slice(0, 5))
        })

        return closeWebSocket
    }, [])

    const handleTransactionSubmit = async (transactions: Transaction[]) => {
        try {
            setConsoleTransactions(prev => [...prev, ...transactions].slice(-50))
            await sendTransaction(transactions)

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

    const fetchTransactions = async (params: { [key: string]: string | number } = {}) => {
        try {
            //const response = await getTransactions({ ...params, size: 10, ...(nextPagingState && { nextpagingstate: nextPagingState }) })
            const response = await getTransactions({ ...params, size: 10 })
            setTransactions(response.transactions)
            setHasNext(response.hasNext)
            setNextPagingState(response.nextPagingState)
        } catch (error) {
            console.error('Error:', error)
            toast({
                title: "Error",
                description: "Failed to fetch transactions",
                variant: "destructive",
            })
        }
    }

    const handlePageChange = (direction: 'prev' | 'next') => {
        if (direction === 'prev' && currentPage > 1) {
            setCurrentPage(prev => prev - 1)
            setNextPagingState(null)
            fetchTransactions(filterParams)
        } else if (direction === 'next' && hasNext) {
            setCurrentPage(prev => prev + 1)
            fetchTransactions({
                ...filterParams,
                ...(nextPagingState ? { nextpagingstate: nextPagingState } : {})
            })
        }
    }


    const handleFilter = async (params: { [key: string]: string | number }) => {
        setFilterParams(params)
        setCurrentPage(1)
        setNextPagingState(null)
        fetchTransactions(params)
    }


    return (
        <div className="container mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 className="text-5xl sm:text-4xl font-bold mb-8 text-center pt-4">
            Advanced Real-Time Payment Processing Dashboard
        </h1>

        <div className="grid grid-cols-1 lg:grid-cols-4 gap-6 mb-8">
            <TransactionForm
                onSubmit={handleTransactionSubmit} />
            <div className="lg:col-span-2">
                <TransactionConsole transactions={consoleTransactions} />
            </div>
            <NotificationList notifications={notifications} />
        </div>

        <Accordion type="single" collapsible className="mb-8">
            <AccordionItem value="transaction-filter-and-table">
                <AccordionTrigger>Transaction Filter and Table</AccordionTrigger>
                <AccordionContent>
                    <div className="space-y-6">
                        <TransactionFilter onFilter={handleFilter} />
                        <TransactionTable
                            transactions={transactions}
                            currentPage={currentPage}
                            hasNext={hasNext}
                            onPageChange={handlePageChange}
                        />
                    </div>
                </AccordionContent>
            </AccordionItem>
        </Accordion>

        <div className="mt-8">
            <KibanaDashboard />
        </div>
        <Toaster />
    </div>
)
}