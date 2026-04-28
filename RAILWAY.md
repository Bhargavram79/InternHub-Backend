# InternHub Backend — Railway Deployment

## Stack
- Java 17 + Spring Boot 3.3.5
- MySQL (via Railway plugin)
- JWT Authentication

## Required Environment Variables

Set these in Railway service Variables tab:

| Variable | Description |
|---|---|
| `DB_URL` | `jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/${{MySQL.MYSQLDATABASE}}?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true` |
| `DB_USERNAME` | `${{MySQL.MYSQLUSER}}` |
| `DB_PASSWORD` | `${{MySQL.MYSQLPASSWORD}}` |
| `JWT_SECRET` | Any long random string (min 32 chars) |
| `FRONTEND_URL` | Your frontend URL (e.g. https://bhargavram79.github.io) |

## Deploy Steps
1. Create Railway project
2. Add MySQL plugin
3. Add this GitHub repo as a service
4. Set the environment variables above
5. Deploy!
