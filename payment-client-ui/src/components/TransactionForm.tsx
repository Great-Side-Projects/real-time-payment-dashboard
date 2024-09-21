import { useState } from 'react'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { getRandomCountryCode, getRandomUserid } from '../utils'
import { Transaction } from '../types'
import { UserIds } from '../utils'

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

  return (
    <Card>
      <CardHeader>
        <CardTitle>Create Transaction</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <Input
            type="number"
            placeholder="Amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            aria-label="Transaction amount"
          />
          <Select value={status} onValueChange={(value) => setStatus(value as 'success' | 'failure')}>
            <SelectTrigger aria-label="Transaction status">
              <SelectValue placeholder="Select status" />
            </SelectTrigger>
            
            <SelectContent>
              <SelectItem value="success">Success</SelectItem>
              <SelectItem value="failure">Failure</SelectItem>
            </SelectContent>
          </Select>
          <Select value={userid} onValueChange={setUserid}>
            <SelectTrigger aria-label="User ID">
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
          <div className="flex space-x-2">
            <Button onClick={() => createTransaction()}>Send Transaction</Button>
            <div className="flex items-center space-x-2">
              <Button variant="outline" onClick={() => createTransaction(true)}>
               Generate Random Transaction
              </Button>
              <Input
                type="number"
               value={bulkCount}
                onChange={(e) => setBulkCount(e.target.value)}
               max={10}
               min={1}
               className="w-20"
               aria-label="Number of random transactions" 
               readOnly={!bulkCount}
              />
            </div>
          </div>
        </div>
      </CardContent>
    </Card>
  )
}