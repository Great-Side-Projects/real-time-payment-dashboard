import { Transaction } from '../types'
import axios from 'axios';


const API_BASE_INGESTION_URL = `${process.env.NEXT_PUBLIC_PAYMENT_INGESTION_URL}/transactions/process`;
const API_BASE_PROCESSING_URL = `${process.env.NEXT_PUBLIC_PAYMENT_PROCESSING_URL}/transactions/filter`;

export const sendTransaction = async (transactions: Transaction[]) => {
  console.log('API_BASE_PROCESSING_URL ', API_BASE_INGESTION_URL);
  const response = await axios.post(`${API_BASE_INGESTION_URL}`, transactions);
  return response.data;
};


interface TransactionParams {
  [key: string]: string | number | boolean;
}

export const getTransactions = async (params: TransactionParams) => {
  console.log('API_BASE_PROCESSING_URL ', API_BASE_PROCESSING_URL);
  const response = await axios.get(`${API_BASE_PROCESSING_URL}`, { params });
  return response.data;
};