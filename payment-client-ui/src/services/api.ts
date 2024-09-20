import { Transaction } from '../types'

export const sendTransaction = async (transaction: Transaction[]): Promise<void> => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_PAYMENT_INGESTION_URL}/transactions/process`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(transaction)
  })
  if (!response.ok) throw new Error('Failed to process transaction')
}