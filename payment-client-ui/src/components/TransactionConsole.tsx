import React, { useEffect, useRef } from 'react'
import { ScrollArea } from "@/components/ui/scroll-area"
import { Transaction } from '../types'

type TransactionConsoleProps = {
  transactions: Transaction[]
}

export default function TransactionConsole({ transactions }: TransactionConsoleProps) {
  const scrollAreaRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    if (scrollAreaRef.current) {
      const scrollContainer = scrollAreaRef.current.querySelector('[data-radix-scroll-area-viewport]')
      if (scrollContainer) {
        scrollContainer.scrollTop = scrollContainer.scrollHeight
      }
    }
  }, [transactions])

  return (
    <div className="bg-black text-green-400 rounded-lg p-4 mt-4">
      <h3 className="text-xl mb-2">Transaction Console</h3>
      <ScrollArea className="h-60" ref={scrollAreaRef}>
        {transactions.map((transaction, index) => (
          <div key={index} className="mb-1">
            <span className="text-yellow-400">$</span> Transaction sent: 
            ID: {transaction.id.slice(0, 8)}... | 
            Amount: ${transaction.amount} | 
            Status: {transaction.status} | 
            User: {transaction.userid}
          </div>
        ))}
      </ScrollArea>
    </div>
  )
}