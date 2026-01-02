# 2️⃣ Transaction Service (Deposit / Withdraw / Transfer)

## 🎯 Responsibility
Handles **all money movements**, including:
- 💵 Deposit
- 💸 Withdraw
- 🔁 Transfer between accounts

---

## 🗄️ Minimal Database Tables

### 🧾 `transaction`
| Field           | Type        | Description |
|-----------------|------------|-------------|
| `id`            | UUID / Long | Primary transaction ID |
| `type`          | Enum        | `DEPOSIT | WITHDRAW | TRANSFER` |
| `fromAccountId` | UUID / Long | Nullable (not needed for deposits) |
| `toAccountId`   | UUID / Long | Nullable (not needed for withdrawals) |
| `amount`        | Decimal     | Transaction amount |
| `status`        | Enum        | `PENDING | COMPLETED | FAILED` |
| `createdAt`     | Timestamp   | Created time |
| `description`   | String      | Optional notes |

---

## 🧠 Simplified Ledger Strategy (For Now)
To keep it simple:
- ✔️ Use a **single `transaction` table**
- ✔️ Let **Account Service update balances**
- ❌ No complex double-entry ledger yet

---

## 🔁 Example Flow: Deposit

**Client →** `POST /transactions/deposit`

**Transaction Service does:**
1️⃣ Validate input  
2️⃣ Call
