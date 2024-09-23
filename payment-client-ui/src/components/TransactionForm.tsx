import { useState } from 'react'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { getRandomCountryCode, getRandomUserid } from '../utils'
import { Transaction } from '../types'
import { UserIds } from '../utils'
import { PlusIcon, MinusIcon } from 'lucide-react'

type TransactionFormProps = {
  onSubmit: (transaction: Transaction[]) => void
}

export default function TransactionForm({ onSubmit }: TransactionFormProps) {
  const [amount, setAmount] = useState('')
  const [status, setStatus] = useState<'success' | 'failure'>('success')
  const [userid, setUserid] = useState('U1')
  const [bulkCount, setBulkCount] = useState('1')

  const createTransaction = (isRandom = false) => {
    
    let count = 1
    if(isRandom) 
      count = parseInt(bulkCount)
   
    const transactions: Transaction[] = Array.from({ length: count }, () => ({
      id: crypto.randomUUID(),
      amount: isRandom ? Math.floor(Math.random() * 2000) : parseInt(amount) ? parseInt(amount) : 0,
      status: isRandom ? (Math.random() < 0.5 ? 'success' : 'failure') : status,
      time: new Date().toISOString(),
      location: isRandom ? getRandomCountryCode() : "US",
      userid: isRandom ? getRandomUserid() : userid
    }))
    onSubmit(transactions)
  }

  const incrementBulkCount = () => {
    setBulkCount(prev => Math.min(parseInt(prev) + 1, 10).toString())
  }
  
  const decrementBulkCount = () => {
    setBulkCount(prev => Math.max(parseInt(prev) - 1, 1).toString())
  }

  return (
    <Card>
      <CardHeader>
        <CardTitle>Create Transaction</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div className="space-y-2">
              <label htmlFor="amount" className="text-sm font-medium">Amount</label>
              <Input
                id="amount"
                type="number"
                placeholder="Amount"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                className="w-full"
              />
            </div>
            <div className="space-y-2">
              <label htmlFor="status" className="text-sm font-medium">Status</label>
              <Select value={status} onValueChange={(value) => setStatus(value as 'success' | 'failure')}>
                <SelectTrigger id="status" className="w-full">
                  <SelectValue placeholder="Select status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="success">Success</SelectItem>
                  <SelectItem value="failure">Failure</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </div>
          <div className="space-y-2">
            <label htmlFor="userid" className="text-sm font-medium">User ID</label>
            <Select value={userid} onValueChange={setUserid}>
              <SelectTrigger id="userid" className="w-full">
                <SelectValue placeholder="Select user ID" />
              </SelectTrigger>
              <SelectContent>
                {UserIds.map((id) => (
                  <SelectItem key={id} value={id}>
                    {id}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <Button onClick={() => createTransaction()} className="w-full">Send Transaction</Button>
            <div className="flex items-center space-x-2">
              <Button variant="outline" onClick={() => createTransaction(true)} className="flex-grow">
              Send Random Transaction
              </Button>
            <div className="flex items-center border rounded-md">
                <Button 
                  variant="ghost" 
                  size="icon"
                  onClick={decrementBulkCount}
                  disabled={parseInt(bulkCount) <= 1}
                  className="px-2 py-1"
                >
                  <MinusIcon className="h-4 w-4" />
                </Button>
                <span className="px-2 py-1 text-center min-w-[40px]">{bulkCount}</span>
                <Button 
                  variant="ghost" 
                  size="icon"
                  onClick={incrementBulkCount}
                  disabled={parseInt(bulkCount) >= 10}
                  className="px-2 py-1"
                >
                  <PlusIcon className="h-4 w-4" />
                </Button>
                </div>
              </div>
          </div>
        </div>
      </CardContent>

    </Card>
  )
}