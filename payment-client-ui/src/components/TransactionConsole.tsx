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
    <div className="bg-black text-green-400 font-mono rounded-lg p-4">
      <h3 className="text-lg mb-2">Transaction Console</h3>
      <ScrollArea className="h-[479px]" ref={scrollAreaRef}>
        {transactions.map((transaction, index) => (
          <div key={index} className="mb-1 text-[10px] leading-tight">
            <span className="text-yellow-400">$</span> Transaction sent: 
            ID: {transaction.id} | 
            Amount: ${transaction.amount} | 
            Status: {transaction.status} | 
            User: {transaction.userid}
          </div>
        ))}
      </ScrollArea>
    </div>
  )
}