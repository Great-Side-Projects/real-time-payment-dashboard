import { useState } from 'react'
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"

type FilterParams = {
  status?: string;
  userid?: string;
  minamount?: number;
  maxamount?: number;
  transactionid?: string;
}

type TransactionFilterProps = {
  onFilter: (params: FilterParams) => void;
}

export default function TransactionFilter({ onFilter }: TransactionFilterProps) {
  const [status, setStatus] = useState<string>('')
  const [userid, setUserid] = useState<string>('')
  const [minamount, setMinamount] = useState<string>('')
  const [maxamount, setMaxamount] = useState<string>('')
  const [transactionid, setTransactionid] = useState<string>('')

  const handleFilter = () => {
    const params: FilterParams = {}
    if (status && status !== 'all') params.status = status
    if (userid) params.userid = userid
    if (minamount) params.minamount = parseFloat(minamount)
    if (maxamount) params.maxamount = parseFloat(maxamount)
    if (transactionid) params.transactionid = transactionid
    onFilter(params)
  }

  return (
    <Card>
      <CardHeader>
        <CardTitle>Search Transactions</CardTitle>
      </CardHeader>
      <CardContent>
    <div className="space-y-4 p-4 bg-white rounded-lg shadow">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div className="space-y-2">
        <label htmlFor="status" className="text-sm font-medium">Status</label>
          <Select value={status} onValueChange={setStatus}>
            <SelectTrigger id="status">
              <SelectValue placeholder="Select status" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="all">All</SelectItem>
              <SelectItem value="success">Success</SelectItem>
              <SelectItem value="failure">Failure</SelectItem>
            </SelectContent>
          </Select>
        </div>
        <div className="space-y-2">
          <label htmlFor="userid" className="text-sm font-medium">User ID</label>
          <Input id="userid" value={userid} onChange={(e) => setUserid(e.target.value)} placeholder="Enter User ID" />
        </div>
        <div className="space-y-2">
          <label htmlFor="minamount" className="text-sm font-medium">Min Amount</label>
          <Input id="minamount" type="number" value={minamount} onChange={(e) => setMinamount(e.target.value)} placeholder="Min Amount" />
        </div>
        <div className="space-y-2">
          <label htmlFor="maxamount" className="text-sm font-medium">Max Amount</label>
          <Input id="maxamount" type="number" value={maxamount} onChange={(e) => setMaxamount(e.target.value)} placeholder="Max Amount" />
        </div>
        <div className="space-y-2 md:col-span-2">
          <label htmlFor="transactionid" className="text-sm font-medium">Transaction ID</label>
          <Input id="transactionid" value={transactionid} onChange={(e) => setTransactionid(e.target.value)} placeholder="Enter Transaction ID" />
        </div>
      </div>
      <Button onClick={handleFilter} className="w-full">Apply Filters</Button>
    </div>
    </CardContent>
    </Card>  )
}