import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { AlertTriangle, DollarSign } from 'lucide-react'
import { Notification } from '../types'

type NotificationListProps = {
  notifications: Notification[]
}

export default function NotificationList({ notifications }: NotificationListProps) {
  return (
    <Card>
      <CardHeader>
        <CardTitle>Recent Notifications</CardTitle>
      </CardHeader>
      <CardContent>
      <div className="h-[400px] overflow-y-auto pr-2">
          {notifications.map((notification, index) => (
            <Alert 
              key={index} 
              variant={notification.type === 'highamountnotification' ? 'default' : 'destructive'}
            >
              {notification.type === 'highamountnotification' ? (
                <DollarSign className="h-4 w-4" />
              ) : (
                <AlertTriangle className="h-4 w-4" />
              )}
              <AlertTitle>{notification.type === 'highamountnotification' ? 'High Amount' : 'Failure'} Notification</AlertTitle>
              <AlertDescription>
                ID: {notification.Transaction.id}<br />
                Amount: ${notification.Transaction.amount}<br />
                Status: {notification.Transaction.status}<br />
                Location: {notification.Transaction.location}<br />
                User ID: {notification.Transaction.userid}
              </AlertDescription>
            </Alert>
          ))}
        </div>
      </CardContent>
    </Card>
  )
}