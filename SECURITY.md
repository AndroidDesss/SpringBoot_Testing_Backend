# zmartCMS API — Setup & Security Guide

## Quick Start

### 1. Generate secret keys
```bash
openssl rand -base64 32   # → JWT_SECRET
openssl rand -base64 32   # → ENCRYPTION_KEY
```

### 2. Create the database
```sql
CREATE DATABASE zcmsdemo4_db;
```

### 3. Set environment variables

**Mac / Linux:**
```bash
export DB_USERNAME=root
export DB_PASSWORD=your_mysql_password
export JWT_SECRET=your_base64_jwt_key
export ENCRYPTION_KEY=your_base64_encryption_key
export CORS_ALLOWED_ORIGINS=http://localhost:3000
```

**Windows PowerShell:**
```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
$env:JWT_SECRET="your_base64_jwt_key"
$env:ENCRYPTION_KEY="your_base64_encryption_key"
$env:CORS_ALLOWED_ORIGINS="http://localhost:3000"
```

**VS Code:** Edit `.vscode/launch.json` and fill in the `env` section.

### 4. Run
```bash
mvn spring-boot:run
```

---

## Swagger UI

Once running, open: **http://localhost:8080/swagger-ui.html**

To test protected endpoints:
1. Call `POST /api/auth/login` in the Auth section.
2. Copy the `accessToken` from the response.
3. Click the **Authorize 🔒** button at the top.
4. Enter: `Bearer <your_token>`
5. All admin endpoints are now unlocked.

OpenAPI JSON spec: http://localhost:8080/v3/api-docs

---

## Required Environment Variables

| Variable | Required | Description | How to generate |
|---|---|---|---|
| `DB_USERNAME` | ✅ | MySQL username | Your DB credentials |
| `DB_PASSWORD` | ✅ | MySQL password | Your DB credentials |
| `JWT_SECRET` | ✅ | Base64-encoded 256-bit JWT signing key | `openssl rand -base64 32` |
| `ENCRYPTION_KEY` | ✅ | Base64-encoded 256-bit key for encrypting mail passwords | `openssl rand -base64 32` |
| `CORS_ALLOWED_ORIGINS` | ⚠️ | Comma-separated allowed frontend origins | Defaults to localhost:3000/3001 |

---

## Security Features

| Feature | Detail |
|---|---|
| JWT signing | HMAC-SHA256, clean Base64 key (fixed double-encoding bug) |
| BCrypt strength | 12 rounds (up from 10) |
| Token blacklist | Logout immediately invalidates both access + refresh tokens |
| Blacklist purge | Expired tokens purged hourly via `@Scheduled` |
| Rate limiting | Auth: 5 req/min/IP · Public: 60 req/min/IP (Bucket4j) |
| Security headers | CSP, X-Frame-Options: DENY, Referrer-Policy, nosniff |
| CORS | Explicit allowed origins, explicit allowed headers |
| Mail password | AES-256-GCM encrypted at rest, decrypted in memory only |
| Input sanitization | HTML tags, `javascript:`, and event handlers stripped on contact form |
| Submissions endpoints | Now require ADMIN role (were publicly accessible) |
| Reserved word columns | `key`/`value` renamed to `field_key`/`field_value` |

---

## API Endpoint Summary

### Auth (rate-limited: 5 req/min/IP)
| Method | Path | Auth | Description |
|---|---|---|---|
| POST | `/api/auth/login` | Public | Returns JWT tokens |
| POST | `/api/auth/logout` | Public | Blacklists tokens |

### Public (rate-limited: 60 req/min/IP)
| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/api/public/banners` | None | List banners |
| GET | `/api/public/blogs` | None | List blogs (paginated) |
| GET | `/api/public/blogs/{url}` | None | Blog by URL |
| GET | `/api/public/blogs/categories` | None | Blog categories |
| GET | `/api/public/events` | None | List events |
| GET | `/api/public/faqs` | None | FAQs by page |
| GET | `/api/public/testimonials` | None | Testimonials |
| GET | `/api/public/seo` | None | SEO data |
| GET | `/api/public/contact-info` | None | Contact info |
| GET | `/api/public/social-media` | None | Social media links |
| POST | `/api/public/contact` | None | Submit contact form |
| POST | `/api/public/newsletter/subscribe` | None | Subscribe |
| GET | `/api/public/contact/submissions` | **ADMIN** | Contact submissions |
| GET | `/api/public/newsletter` | **ADMIN** | Subscribers list |

### Admin (requires Bearer JWT — ADMIN or SUPER_ADMIN)
All `/api/admin/**` — see Swagger UI for full details.

### Super Admin only
| Method | Path | Description |
|---|---|---|
| GET/POST | `/api/admin/mail-config` | SMTP config (password encrypted) |
