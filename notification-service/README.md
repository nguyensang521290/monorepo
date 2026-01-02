# 4️⃣ Notification Service

## 🎯 Responsibility
Handles **sending notifications only** — **no business logic**.

📌 Initially:
- You can simply log messages to console
- Or store notifications in DB

---

## ✨ Minimal Features
Send notifications on:
- 💵 Deposit
- 💸 Withdrawal
- 🔁 Transfer
- ✅ Loan Approval
- 💰 Loan Repayment

---

## 🗄️ Minimal Database Tables

### 🧾 `notification`
| Field        | Type        | Description |
|-------------|-------------|-------------|
| `id`        | UUID / Long | Notification ID |
| `customerId` / `accountId` | UUID / Long | Recipient reference |
| `type`      | Enum        | `DEPOSIT_SUCCESS | WITHDRAW_SUCCESS | TRANSFER_SUCCESS | LOAN_APPROVED | LOAN_REPAID | ...` |
| `message`   | String      | Notification text |
| `createdAt` | Timestamp   | Created time |
| `status`    | Enum        | `SENT | FAILED` |

---

## 🌐 APIs

### 📩 Send Notification
**`POST /notifications`**

#### Request Body
```json
{
  "customerId": "12345",
  "type": "DEPOSIT_SUCCESS",
  "message": "You deposited $500 successfully."
}
```

---

## 🤝 Integration

### ✔️ Transaction Service calls after:
- Deposit success
- Withdrawal success
- Transfer success

### ✔️ Loan Service calls after:
- Loan approved
- Loan r
