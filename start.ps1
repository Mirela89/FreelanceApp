#Write-Host "Starting PostgreSQL container..." -ForegroundColor Green
#docker-compose up -d

#Write-Host "Waiting for database to be ready..." -ForegroundColor Yellow
#Start-Sleep -Seconds 3

$backend = Start-Job -ScriptBlock {
    Set-Location "$using:PWD\backend"
    ./mvnw spring-boot:run
}

$frontend = Start-Job -ScriptBlock {
    Set-Location "$using:PWD\frontend"
    ng serve
}

Write-Host "All services starting!" -ForegroundColor Cyan
Write-Host "Backend:  http://localhost:8081" -ForegroundColor White
Write-Host "Frontend: http://localhost:4200" -ForegroundColor White
Write-Host "Press Ctrl+C to stop all services..." -ForegroundColor Yellow

try {
    while ($true) {
        Receive-Job $backend
        Receive-Job $frontend
        Start-Sleep -Milliseconds 500
    }
} finally {
    # Ruleaza automat la Ctrl+C
    Write-Host "Stopping all services..." -ForegroundColor Red
    Stop-Job $backend
    Stop-Job $frontend
    Remove-Job $backend
    Remove-Job $frontend
#    docker-compose stop
    Write-Host "All services stopped." -ForegroundColor Green
}