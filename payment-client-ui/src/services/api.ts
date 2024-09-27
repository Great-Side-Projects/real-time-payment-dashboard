import { Transaction } from '../types'
import axios from 'axios';


const API_BASE_PROCESSING_URL = `${process.env.NEXT_PUBLIC_PAYMENT_URL}/transactions/filter`;
const API_BASE_INGESTION_URL = `${process.env.NEXT_PUBLIC_PAYMENT_URL}/transactions/process`;

export const sendTransaction = async (transactions: Transaction[]) => {
  const response = await axios.post(`${API_BASE_INGESTION_URL}`, transactions);
  return response.data;
};


interface TransactionParams {
  [key: string]: string | number | boolean;
}

export const getTransactions = async (params: TransactionParams) => {
  const response = await axios.get(`${API_BASE_PROCESSING_URL}`, { params });
  return response.data;
};