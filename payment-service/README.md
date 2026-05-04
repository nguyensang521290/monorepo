# 1️⃣ Account Service

## 🎯 Responsibility
Manage **bank accounts only**  
(Not responsible for managing users — keeping it super simple 😎)

---

## ✨ Main Features
- 📥 **Open Account**
- 📄 **Get Account Details**
- 💰 **Get Current Balance**

---

## 🗄️ Minimal Database Tables

### 🧾 `account`
| Field            | Type              | Description |
|------------------|-------------------|-------------|
| `id`             | UUID / Long       | Primary ID |
| `customerId`     | String / Long     | Reference to customer |
| `accountNumber`  | String            | Unique account number |
| `currency`       | String            | e.g., VND, USD |
| `status`         | Enum              | `ACTIVE | FROZEN | CLOSED` |
| `createdAt`      | Timestamp         | Creation timestamp |

---

### 💵 `account_balance`
| Field        | Type        | Description |
|-------------|-------------|-------------|
| `accountId` | UUID / Long | FK to `account.id` |
| `balance`   | Decimal     | Current account balance |

---

## 🌐 Important APIs

### ➕ Create Account
**`POST /accounts`**

#### Request Body
```json
{
  "customerId": "12345",
  "currency": "USD"
}
```

✔️ Creates a new account  
✔️ Initial balance = `0`

---

### 📄 Get Account Details
**`GET /accounts/{id}`**

---

### 💰 Get Account Balance
**`GET /accounts/{id}/balance`**

---

## ⚠️ Note
> For now, **don’t implement complex ledger logic here**.  
> Balance should be updated by **Transaction Service via REST**.
