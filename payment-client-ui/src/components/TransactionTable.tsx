import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
  } from "@/components/ui/table"
  import {
    Pagination,
    PaginationContent,
    PaginationItem,
    PaginationLink,
    PaginationNext,
  } from "@/components/ui/pagination"
  import { Transaction } from '../types'
  
  
  type TransactionTableProps = {
    transactions: Transaction[]
    currentPage: number
    hasNext: boolean
    onPageChange: (direction: 'prev' | 'next') => void
  }
  import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
  
  export default function TransactionTable({ 
    transactions, 
    currentPage, 
    hasNext,
    onPageChange 
  }: TransactionTableProps) {
    return (
        <Card>
      <CardHeader>
        <CardTitle className="text-center">Transactions</CardTitle>
      </CardHeader>
      <CardContent>
      <div className="space-y-4">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>ID</TableHead>
              <TableHead>User ID</TableHead>
              <TableHead>Amount</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>Time</TableHead>
              <TableHead>Location</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {transactions.map((transaction) => (
              <TableRow key={transaction.id}>
                <TableCell className="font-medium">{transaction.id}</TableCell>
                <TableCell>{transaction.userid}</TableCell>
                <TableCell>${transaction.amount.toFixed(2)}</TableCell>
                <TableCell>{transaction.status}</TableCell>
                <TableCell>{new Date(transaction.time).toLocaleString()}</TableCell>
                <TableCell>{transaction.location}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <Pagination>
          <PaginationContent>
              {/* 
              <PaginationItem>
              <PaginationPrevious 
                onClick={() => onPageChange('prev')} 
                className={currentPage === 1 ? 'disabled' : ''}
                aria-disabled={currentPage === 1}
              />
            </PaginationItem>
              */}
            <PaginationItem>
              <PaginationLink>{currentPage}</PaginationLink>
            </PaginationItem>
            <PaginationItem>
              <PaginationNext 
                onClick={() => onPageChange('next')} 
                aria-disabled={!hasNext}
              />
            </PaginationItem>
          </PaginationContent>
        </Pagination>
      </div>
      </CardContent>
    </Card>
    )
  }