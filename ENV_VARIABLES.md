# 🌍 Production Environment Variables

This file lists all environment variables needed for deployment. Set these in your hosting platform (Railway, Heroku, AWS, etc.).

---

## 📝 Quick Copy-Paste for Deployment

### **Required Variables:**

```bash
# Database
DATABASE_URL=jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:6543/postgres?user=postgres.jpjowrtzyyswjiecljti&password=Mrinank@13
DB_USERNAME=postgres.jpjowrtzyyswjiecljti
DB_PASSWORD=Mrinank@13

# Supabase
SUPABASE_URL=https://jpjowrtzyyswjiecljti.supabase.co
SUPABASE_ANON_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impwam93cnR6eXlzd2ppZWNsanRpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA2Nzc2OTQsImV4cCI6MjA3NjI1MzY5NH0.z-dABy9QEr57KzP7cJTv8NlC5lu5Y87FhOGIo9VkNac
SUPABASE_JWT_SECRET=QrVbrK081WYFCLnHY3YSS6Q7UcfquPn9+hY8BVAPxg7tjCZXezg0q1HFyOev8s2o2s2+t+zVgixOlsvN/NFIMA==

# Storage
SUPABASE_STORAGE_BUCKET=car-images

# CORS - **IMPORTANT: Update this with your actual frontend URL!**
FRONTEND_URL=https://your-app.vercel.app

# Server
PORT=8080

# Logging (Optional - for production)
LOG_LEVEL=INFO
APP_LOG_LEVEL=INFO
SHOW_SQL=false
```

---

## 🔧 Platform-Specific Setup

### **Railway** 🚂

```bash
# In Railway dashboard, go to: Variables tab
# Click "Add Variable" for each:

DATABASE_URL=jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:6543/postgres?user=postgres.jpjowrtzyyswjiecljti&password=Mrinank@13
DB_USERNAME=postgres.jpjowrtzyyswjiecljti
DB_PASSWORD=Mrinank@13
SUPABASE_URL=https://jpjowrtzyyswjiecljti.supabase.co
SUPABASE_ANON_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impwam93cnR6eXlzd2ppZWNsanRpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA2Nzc2OTQsImV4cCI6MjA3NjI1MzY5NH0.z-dABy9QEr57KzP7cJTv8NlC5lu5Y87FhOGIo9VkNac
SUPABASE_JWT_SECRET=QrVbrK081WYFCLnHY3YSS6Q7UcfquPn9+hY8BVAPxg7tjCZXezg0q1HFyOev8s2o2s2+t+zVgixOlsvN/NFIMA==
SUPABASE_STORAGE_BUCKET=car-images
FRONTEND_URL=https://your-frontend.vercel.app
```

### **Heroku** 🟣

```bash
# Using Heroku CLI:
heroku config:set DATABASE_URL="jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:6543/postgres?user=postgres.jpjowrtzyyswjiecljti&password=Mrinank@13"
heroku config:set DB_USERNAME="postgres.jpjowrtzyyswjiecljti"
heroku config:set DB_PASSWORD="Mrinank@13"
heroku config:set SUPABASE_URL="https://jpjowrtzyyswjiecljti.supabase.co"
heroku config:set SUPABASE_ANON_KEY="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impwam93cnR6eXlzd2ppZWNsanRpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA2Nzc2OTQsImV4cCI6MjA3NjI1MzY5NH0.z-dABy9QEr57KzP7cJTv8NlC5lu5Y87FhOGIo9VkNac"
heroku config:set SUPABASE_JWT_SECRET="QrVbrK081WYFCLnHY3YSS6Q7UcfquPn9+hY8BVAPxg7tjCZXezg0q1HFyOev8s2o2s2+t+zVgixOlsvN/NFIMA=="
heroku config:set SUPABASE_STORAGE_BUCKET="car-images"
heroku config:set FRONTEND_URL="https://your-frontend.vercel.app"
```

### **AWS Elastic Beanstalk** ☁️

Create a file `.ebextensions/environment.config`:

```yaml
option_settings:
  aws:elasticbeanstalk:application:environment:
    DATABASE_URL: "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:6543/postgres?user=postgres.jpjowrtzyyswjiecljti&password=Mrinank@13"
    DB_USERNAME: "postgres.jpjowrtzyyswjiecljti"
    DB_PASSWORD: "Mrinank@13"
    SUPABASE_URL: "https://jpjowrtzyyswjiecljti.supabase.co"
    SUPABASE_ANON_KEY: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    SUPABASE_JWT_SECRET: "QrVbrK081WYFCLnHY3YSS6Q7UcfquPn9+hY8BVAPxg7tjCZXezg0q1HFyOev8s2o2s2+t+zVgixOlsvN/NFIMA=="
    SUPABASE_STORAGE_BUCKET: "car-images"
    FRONTEND_URL: "https://your-frontend.vercel.app"
```

---

## ⚠️ **IMPORTANT: Update FRONTEND_URL**

After deploying your frontend, update the `FRONTEND_URL` variable with your actual frontend URL:

**Examples:**
- Vercel: `https://car-showcase-erp.vercel.app`
- Netlify: `https://car-showcase-erp.netlify.app`
- Custom: `https://yourapp.com`

**For multiple frontend URLs (staging + production):**
```bash
FRONTEND_URL=https://staging.yourapp.com,https://yourapp.com
```

---

## 🧪 Testing Environment Variables

### Test Locally with Production Settings:

**Option 1: Temporary (current session)**
```bash
# Windows PowerShell
$env:FRONTEND_URL="http://localhost:3000"; ./mvnw spring-boot:run

# macOS/Linux
FRONTEND_URL=http://localhost:3000 ./mvnw spring-boot:run
```

**Option 2: Using .env file (with Spring Boot)**

1. Install `spring-boot-dotenv` (optional):
```xml
<!-- Add to pom.xml if you want .env file support -->
<dependency>
    <groupId>me.paulschwarz</groupId>
    <artifactId>spring-dotenv</artifactId>
    <version>4.0.0</version>
</dependency>
```

2. Create `.env` in backend folder (add to .gitignore!)
3. Copy variables from above

---

## 📋 Variable Descriptions

| Variable | Description | Example |
|----------|-------------|---------|
| `DATABASE_URL` | Full JDBC connection string | `jdbc:postgresql://...` |
| `DB_USERNAME` | PostgreSQL username | `postgres.xxxxx` |
| `DB_PASSWORD` | PostgreSQL password | `YourPassword123` |
| `SUPABASE_URL` | Supabase project URL | `https://xxx.supabase.co` |
| `SUPABASE_ANON_KEY` | Supabase anon/public key | `eyJ...` (JWT token) |
| `SUPABASE_JWT_SECRET` | JWT signing secret | Base64 string |
| `SUPABASE_STORAGE_BUCKET` | Storage bucket name | `car-images` |
| `FRONTEND_URL` | Frontend URL(s) for CORS | `https://app.vercel.app` |
| `PORT` | Server port (auto-assigned) | `8080` |
| `LOG_LEVEL` | Logging level | `INFO`, `DEBUG`, `WARN` |
| `APP_LOG_LEVEL` | App-specific log level | `INFO`, `DEBUG` |
| `SHOW_SQL` | Show SQL queries in logs | `false` (production) |

---

## 🔒 Security Best Practices

### ✅ DO:
- ✅ Store these variables in your hosting platform's secure vault
- ✅ Use different databases for dev/staging/production
- ✅ Rotate secrets periodically
- ✅ Never commit credentials to Git
- ✅ Use environment-specific keys

### ❌ DON'T:
- ❌ Hardcode credentials in source code
- ❌ Commit `.env` files to Git
- ❌ Share production keys in team chat
- ❌ Use same credentials across environments
- ❌ Expose JWT secrets publicly

---

## 🚨 Emergency: Leaked Credentials

If credentials are exposed:

1. **Immediate Actions:**
   - Generate new Supabase keys (Dashboard → Settings → API)
   - Change database password (Supabase → Database → Settings)
   - Update all environment variables
   - Redeploy applications

2. **Prevention:**
   - Add `.env` to `.gitignore`
   - Use secret scanning tools
   - Regular security audits

---

## ✅ Deployment Checklist

Before deploying:

- [ ] All environment variables set in hosting platform
- [ ] `FRONTEND_URL` updated with actual frontend URL
- [ ] Supabase Storage bucket created (`car-images`)
- [ ] RLS disabled or policies configured
- [ ] Database accessible from hosting platform
- [ ] Test environment variables locally first
- [ ] `.env` files added to `.gitignore`
- [ ] Backend builds successfully
- [ ] Health check endpoint works

---

## 📞 Need Help?

- Check `DEPLOYMENT.md` for complete deployment guide
- Check `SUPABASE_STORAGE_SETUP.md` for storage configuration
- Verify all variables are set correctly
- Check platform logs for errors

---

**You're ready to deploy! 🚀**

