# 3️⃣ Loan Service (Simple Loan / Credit)

## 🎯 Responsibility
Handle **basic loan logic only** — keep it simple, nothing overly complex.

---

## ✨ Minimal Features
- 🏦 **Create Loan Product**  
  Example: `PERSONAL_LOAN` with interest rate + term (months)

- 📝 **Apply for Loan**
- ✅ **Approve / Reject Loan**
- 💸 **Disburse Loan**
    - Credit money into bank account using **Transaction Service → Deposit**
- 💰 **Repay Loan**
    - Withdraw from account and reduce loan outstanding amount

---

## 🗄️ Minimal Database Tables

### 🧾 `loan_product`
| Field        | Type        | Description |
|-------------|-------------|-------------|
| `id`        | UUID / Long | Primary ID |
| `name`      | String      | Product name |
| `interestRate` | Decimal  | Annual interest rate |
| `termMonths` | Int        | Loan duration |

---

### 🧾 `loan`
| Field             | Type        | Description |
|-------------------|------------|-------------|
| `id`              | UUID / Long | Loan ID |
| `accountId`       | UUID / Long | Borrower account |
| `productId`       | UUID / Long | Linked loan product |
| `principalAmount` | Decimal     | Original loan amount |
| `interestRate`    | Decimal     | Snapshot of product rate |
| `termMonths`      | Int         | Snapshot of product term |
| `status`          | Enum        | `PENDING | APPROVED | ACTIVE | CLOSED` |
| `outstandingAmount` | Decimal   | Remaining debt |
| `createdAt`       | Timestamp   | Created time |

---

### 🧾 `loan_repayment`
| Field    | Type        | Description |
|---------|-------------|-------------|
| `id`    | UUID / Long | Repayment ID |
| `loanId`| UUID / Long | Reference to loan |
| `amount`| Decimal     | Paid amount |
| `paidAt`| Timestamp   | Payment time |

---

## 🔁 Example Flows

### 📝 Apply Loan
**`POST /loans/apply`**

#### Request Body
```json
{
  "accountId": "12345",
  "productId": "PERSONAL_LOAN",
  "amount": 20000
}
```

📌 Result:  
Loan created with `status = PENDING`

---

### ✅ Approve + Disburse Loan
**`POST /loans/{id}/approve`**

Steps:
1️⃣ Set loan → `APPROVED`  
2️⃣ Call **Transaction Service**
```
POST /transactions/deposit
```
3️⃣ On success → set loan → `ACTIVE`

---

### 💰 Repay Loan
**`POST /loans/{id}/repay`**

#### Request Body
```json
{
  "amount": 500
}
```

Steps:
- Call Transaction Service → `withdraw`
- Decrease `outstandingAmount`
- If outstandingAmount ≤ 0 → `status = CLOSED`

---

## ⚠️ Notes
> This version keeps loan handling **simple & practical**.  
> Later you can enhance with:
> - EMI schedule
> - Interest accrual engine
> - Overdue handling
> - Penalties
