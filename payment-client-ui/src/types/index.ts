export type Transaction = {
    id: string
    amount: number
    status: 'success' | 'failure'
    time: string
    location: string
    userid: string
  }
  
  export type Notification = {
    type: 'highamountnotification' | 'failurenotification'
    Transaction: Transaction
  }