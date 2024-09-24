import { useEffect, useRef } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { ScrollArea } from "@/components/ui/scroll-area"
import { AlertCircle, CheckCircle } from "lucide-react"
import { Notification } from "../types"

type NotificationListProps = {
  notifications: Notification[]
}

export default function NotificationList({ notifications }: NotificationListProps) {
  const scrollAreaRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    if (scrollAreaRef.current) {
      const scrollContainer = scrollAreaRef.current.querySelector('[data-radix-scroll-area-viewport]')
      if (scrollContainer) {
        //scrollContainer.scrollTop = 0
      }
    }
  }, [notifications])

  return (
    <Card className="h-full">
      <CardHeader>
        <CardTitle>Recent Notifications</CardTitle>
      </CardHeader>
      <CardContent> 
        <ScrollArea className="h-[400px] pr-3" ref={scrollAreaRef}>
        
          {notifications.map((notification, /*index*/) => (
            <div
              key={notification.Transaction.id}
              className={`mb-4 p-3 rounded-lg  ${
                notification.type === 'highamountnotification' ? 'bg-yellow-100 dark:bg-yellow-900' : 'bg-red-100 dark:bg-red-900'
              } ${'animate-highlight'}`}
            >
              <div className="flex items-start">
                {notification.type === 'highamountnotification' ? (
                  <AlertCircle className="text-yellow-500 dark:text-yellow-400 mr-2 flex-shrink-0 mt-1" />
                ) : (
                  <CheckCircle className="text-red-500 dark:text-red-400 mr-2 flex-shrink-0 mt-1" />
                )}
                <div className="flex-grow min-w-0">
                  <h3 className="font-semibold text-sm mb-1">
                    {notification.type === 'highamountnotification' ? 'High Amount' : 'Failure'} Notification
                  </h3>
                  <p className="text-xs mb-1 break-all">
                    <span className="font-medium">Transaction ID:</span> {notification.Transaction.id}
                  </p>
                  <p className="text-xs mb-1">
                    <span className="font-medium">Amount:</span> ${notification.Transaction.amount.toFixed(2)}
                  </p>
                  <p className="text-xs">
                    <span className="font-medium">User ID:</span> {notification.Transaction.userid}
                  </p>
                    <p className="text-xs">
                    <span className="font-medium">Time:</span> {new Date(notification.Transaction.time).toLocaleString('es-CO', { timeZone: 'America/Bogota' })}
                    </p>
                </div>
              </div>
            </div>
          ))}
        </ScrollArea>
      </CardContent>
    </Card>
  )
}