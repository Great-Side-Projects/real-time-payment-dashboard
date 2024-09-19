import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"

export default function KibanaDashboard() {
  return (
    <Card className="mt-6">
      <CardHeader>
        <CardTitle>Kibana Dashboard</CardTitle>
      </CardHeader>
      <CardContent>
        <iframe
          src={process.env.NEXT_PUBLIC_KIBANA_DASHBOARD_URL}
          className="w-full h-[1080px] border-0"
          title="Kibana Dashboard"
        />
      </CardContent>
    </Card>
  )
}