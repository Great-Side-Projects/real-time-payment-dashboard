import { useState } from 'react'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { getRandomCountryCode, getRandomUserid } from '../utils'
import { Transaction } from '../types'
import { UserIds } from '../utils'

type TransactionFormProps = {
  onSubmit: (transaction: Transaction) => void
}

export default function TransactionForm({ onSubmit }: TransactionFormProps) {
  const [amount, setAmount] = useState('')
  const [status, setStatus] = useState<'success' | 'failure'>('success')
  const [userid, setUserid] = useState('U1')

  const createTransaction = (isRandom = false) => {
    const transaction: Transaction = {
      id: crypto.randomUUID(),
      amount: isRandom ? Math.floor(Math.random() * 1000) : parseInt(amount),
      status: isRandom ? (Math.random() < 0.5 ? 'success' : 'failure') : status,
      time: new Date().toISOString(),
      location: isRandom ? getRandomCountryCode() : "US",
      userid: isRandom ? getRandomUserid() : userid
    }
    onSubmit(transaction)
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
            <Button variant="outline" onClick={() => createTransaction(true)}>
              Generate Random Transaction
            </Button>
          </div>
        </div>
      </CardContent>
    </Card>
  )
}