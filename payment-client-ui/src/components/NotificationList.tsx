import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { AlertCircle, CheckCircle } from "lucide-react"
import { Notification } from "../types"

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
        <div className="h-[450px] overflow-y-auto pr-2">
          {notifications.map((notification, index) => (
            <div
              key={index}
              className={`mb-4 p-4 rounded-lg flex items-start ${
                notification.type === 'highamountnotification' ? 'bg-yellow-100' : 'bg-red-100'
              }`}
            >
              {notification.type === 'highamountnotification' ? (
                <AlertCircle className="text-yellow-500 mr-2 flex-shrink-0" />
              ) : (
                <CheckCircle className="text-red-500 mr-2 flex-shrink-0" />
              )}
              <div>
                <h3 className="font-semibold">
                  {notification.type === 'highamountnotification' ? 'High Amount' : 'Failure'} Notification
                </h3>
                <p className="text-sm">
                  Transaction ID: {notification.Transaction.id}
                </p>
                <p className="text-sm">
                  Amount: ${notification.Transaction.amount.toFixed(2)}
                </p>
                <p className="text-sm">
                  User ID: {notification.Transaction.userid}
                </p>
              </div>
            </div>
          ))}
        </div>
      </CardContent>
    </Card>
  )
}