const fs = require('fs');
const path = require('path');

class Logger {
  constructor(logFilePath) {
    this.logFilePath = logFilePath;
    this.ensureLogFileExists();
  }

  ensureLogFileExists() {
    const logDir = path.dirname(this.logFilePath);
    if (!fs.existsSync(logDir)) {
      fs.mkdirSync(logDir, { recursive: true });
    }
    if (!fs.existsSync(this.logFilePath)) {
      fs.writeFileSync(this.logFilePath, '');
    }
  }

  log(message) {
    const timestamp = new Date().toISOString();
    const logMessage = `Log: ${timestamp} - ${message}\n`;
    fs.appendFileSync(this.logFilePath, logMessage);
  }

  logTransactionProcessed(transaction) {
    const logMessage = `Transaction processed [${transaction.transactionId}] User: ${transaction.userId}, Amount: $${transaction.amount}, Status: ${transaction.status}, Time: ${transaction.timestamp}, Location: ${transaction.location}`;
    this.log(logMessage);
  }

  logNotificationSent(type, transaction) {
    const logMessage = `Notification: ${type} detected! [${transaction.transactionId}] User: ${transaction.userId}, Amount: $${transaction.amount}, Time: ${transaction.timestamp}, Location: ${transaction.location}`;
    this.log(logMessage);
  }
}

module.exports = Logger;
