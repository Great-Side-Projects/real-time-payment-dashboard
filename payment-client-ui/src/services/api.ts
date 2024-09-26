import { Transaction } from '../types'
import axios from 'axios';


const API_BASE_PROCESSIN_URL = `${process.env.NEXT_PUBLIC_PAYMENT_URL}/processing/transactions`;
const API_BASE_INGESTION_URL = `${process.env.NEXT_PUBLIC_PAYMENT_URL}/ingestion/transactions`;


/*
export const sendTransaction = async (transaction: Transaction[]): Promise<void> => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_PAYMENT_INGESTION_URL}/transactions/process`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(transaction)
  })
  if (!response.ok) throw new Error('Failed to process transaction')
}
*/

export const sendTransaction = async (transactions: Transaction[]) => {
  const response = await axios.post(`${API_BASE_INGESTION_URL}/process`, transactions);
  return response.data;
};


interface TransactionParams {
  [key: string]: string | number | boolean;
}

export const getTransactions = async (params: TransactionParams) => {
  const response = await axios.get(`${API_BASE_PROCESSIN_URL}/filter`, { params });
  return response.data;
};