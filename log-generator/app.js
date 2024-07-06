const express = require('express');
const path = require('path');
const Logger = require('./src/logger');
const { v4: uuidv4 } = require('uuid');

const app = express();
const port = 3000;

// Ruta del archivo de log
const logFilePath = path.join(__dirname, 'logs', 'transactions.log');

// Crear una instancia de Logger
const logger = new Logger(logFilePath);

// Función para generar una transacción con UUID, amount dinámico y timestamp actual
function generateTransaction(userId, status, location) {
  const amount = Math.floor(Math.random() * (5000 - 100 + 1)) + 100; // Genera un amount aleatorio entre 100 y 5000
  return {
    transactionId: uuidv4(),
    userId,
    amount,
    status,
    timestamp: new Date().toISOString(),
    location
  };
}

// Función para registrar transacciones cada 10 segundos
function registerTransactions() {
  // Simular generación de nuevas transacciones
  const newTransactions = [
    generateTransaction('U1', 'success', 'NY'),
    generateTransaction('U2', 'success', 'CA'),
    generateTransaction('U1', 'failure', 'NY')
  ];

  // Registrar cada transacción individual en el archivo de log
  newTransactions.forEach(transaction => {
    const logMessage = `Transaction [${transaction.transactionId}] - User: ${transaction.userId}, Amount: $${transaction.amount}, Status: ${transaction.status}, Time: ${transaction.timestamp}, Location: ${transaction.location}`;
    logger.log(logMessage);
  });

  // setea el timer para generar logs cada cierto tiempo
  setTimeout(registerTransactions, 10000); // 10 segundos
}

// Iniciar el registro de transacciones
registerTransactions();

app.get('/', (req, res) => {
  res.send('Real-Time Payment Processing Dashboard is running');
});

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
