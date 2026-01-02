# 5️⃣ Log / Audit Service

## 🎯 Responsibility
Store **audit logs of important actions** so you can trace what happened later.

---

## 📝 What to Log
- 🏦 **Account Events**
    - Account created
- 💸 **Transaction Events**
    - Deposit executed
    - Withdrawal executed
    - Transfer executed
- 🏛 **Loan Events**
    - Loan applied
    - Loan approved
    - Loan repaid
- ❗ **Error Events**
    - Transaction errors
    - Loan processing errors

---

## 🗄️ Minimal Database Tables

### 🧾 `audit_log`
| Field        | Type        | Description |
|-------------|-------------|-------------|
| `id`        | UUID / Long | Log ID |
| `serviceName` | String    | e.g., `ACCOUNT`, `TRANSACTION`, `LOAN`, etc. |
| `action`    | String      | e.g., `CREATE_ACCOUNT`, `DEPOSIT`, `TRANSFER`, etc. |
| `entityId`  | UUID / Long | Related entity (accountId, transactionId, loanId...) |
| `details`   | JSON / Text | Extra information |
| `createdAt` | Timestamp   | When it happened |

---

## 🌐 APIs

### 🧾 Create Audit Log
**`**
