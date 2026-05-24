
import { useState, useEffect } from 'react';
import { CreditCard, CheckCircle, XCircle, LogIn, LogOut, User } from 'lucide-react';

const PRODUCTS = [
  { id: '1', name: 'Premium Coffee Beans', price: '5.00' },
  { id: '2', name: 'Organic Green Tea', price: '5.00' },
  { id: '3', name: 'Artisan Chocolate Bar', price: '0.00' }
];

const App = () => {
  const [user, setUser] = useState<{ userId: number, accessToken: string } | null>(null);
  const [view, setView] = useState<'login' | 'payment'>('login');
  const [selectedProduct, setSelectedProduct] = useState(PRODUCTS[0]);
  const [status, setStatus] = useState('idle');
  const [error, setError] = useState('');

  // Login Form State
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      setUser(JSON.parse(savedUser));
      setView('payment');
    }
  }, []);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setStatus('processing');
    setError('');

    try {
      const response = await fetch('/identity-service/v1/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });

      if (response.ok) {
        const data = await response.json();
        setUser(data);
        localStorage.setItem('user', JSON.stringify(data));
        setView('payment');
        setStatus('idle');
      } else {
        throw new Error('Invalid credentials');
      }
    } catch (err) {
      setStatus('error');
      setError(err instanceof Error ? err.message : 'Login failed');
    }
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('user');
    setView('login');
    setStatus('idle');
    setError('');
  };

  const handlePayment = async () => {
    setStatus('processing');
    setError('');
    
    try {
      const response = await fetch('/order-service/v1/order', {
        method: 'POST',
        headers: { 
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${user?.accessToken}`
        },
        body: JSON.stringify({ products: [selectedProduct.name] })
      });

      if (response.ok) {
        setStatus('success');
      } else {
        const errData = await response.json().catch(() => ({}));
        throw new Error(errData.message || 'Payment failed at gateway');
      }
    } catch (err) {
      setStatus('error');
      setError(err instanceof Error ? err.message : 'Connection failed');
    }
  };

  const containerStyle: React.CSSProperties = { 
    fontFamily: 'system-ui, sans-serif', 
    maxWidth: '400px', 
    margin: '40px auto', 
    padding: '24px', 
    borderRadius: '12px', 
    boxShadow: '0 4px 20px rgba(0,0,0,0.1)', 
    backgroundColor: '#fff' 
  };

  if (view === 'login') {
    return (
      <div style={containerStyle}>
        <header style={{ textAlign: 'center', marginBottom: '24px' }}>
          <LogIn size={48} color="#3b82f6" style={{ marginBottom: '16px' }} />
          <h1 style={{ fontSize: '24px', color: '#1a1a1a', margin: '0 0 8px 0' }}>Welcome Back</h1>
          <p style={{ fontSize: '14px', color: '#666', margin: 0 }}>Login to your account</p>
        </header>

        <form onSubmit={handleLogin}>
          <div style={{ marginBottom: '16px' }}>
            <label style={{ display: 'block', fontSize: '14px', fontWeight: 'bold', marginBottom: '8px' }}>Username</label>
            <input 
              type="text" 
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              style={{ width: '100%', padding: '12px', borderRadius: '8px', border: '1px solid #e5e7eb', boxSizing: 'border-box' }}
              placeholder="Enter your username"
              required
            />
          </div>
          <div style={{ marginBottom: '24px' }}>
            <label style={{ display: 'block', fontSize: '14px', fontWeight: 'bold', marginBottom: '8px' }}>Password</label>
            <input 
              type="password" 
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              style={{ width: '100%', padding: '12px', borderRadius: '8px', border: '1px solid #e5e7eb', boxSizing: 'border-box' }}
              placeholder="••••••••"
              required
            />
          </div>

          {status === 'error' && (
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', padding: '12px', backgroundColor: '#fef2f2', color: '#991b1b', borderRadius: '6px', marginBottom: '16px', fontSize: '14px' }}>
              <XCircle size={16} />
              <span>{error}</span>
            </div>
          )}

          <button 
            type="submit"
            disabled={status === 'processing'}
            style={{ width: '100%', padding: '14px', border: 'none', borderRadius: '8px', backgroundColor: '#3b82f6', color: 'white', fontSize: '16px', fontWeight: '600', cursor: status === 'processing' ? 'not-allowed' : 'pointer', opacity: status === 'processing' ? 0.7 : 1 }}
          >
            {status === 'processing' ? 'Logging in...' : 'Login'}
          </button>
        </form>
      </div>
    );
  }

  return (
    <div style={containerStyle}>
      <header style={{ position: 'relative', textAlign: 'center', marginBottom: '24px' }}>
        <button 
          onClick={handleLogout}
          style={{ position: 'absolute', right: 0, top: 0, border: 'none', background: 'none', cursor: 'pointer', color: '#ef4444', display: 'flex', alignItems: 'center', gap: '4px', fontSize: '12px', fontWeight: '600' }}
        >
          <LogOut size={14} /> Logout
        </button>
        <div style={{ display: 'inline-flex', alignItems: 'center', gap: '8px', color: '#3b82f6', marginBottom: '8px' }}>
           <User size={20} />
           <span style={{ fontSize: '14px', fontWeight: '600' }}>User ID: {user?.userId}</span>
        </div>
        <h1 style={{ fontSize: '24px', color: '#1a1a1a', margin: '0' }}>Payment Gateway</h1>
      </header>

      {status === 'success' ? (
        <div style={{ textAlign: 'center', padding: '32px 0' }}>
          <CheckCircle size={64} color="#10b981" style={{ marginBottom: '16px' }} />
          <h2 style={{ fontSize: '20px', margin: '0 0 8px 0' }}>Payment Success!</h2>
          <p style={{ color: '#666' }}>Your order has been placed.</p>
          <button onClick={() => setStatus('idle')} style={{ marginTop: '24px', padding: '10px 20px', border: 'none', borderRadius: '6px', backgroundColor: '#3b82f6', color: 'white', cursor: 'pointer' }}>New Payment</button>
        </div>
      ) : (
        <>
          <div style={{ marginBottom: '24px' }}>
            <label style={{ display: 'block', fontSize: '14px', fontWeight: 'bold', marginBottom: '8px' }}>Select Product</label>
            {PRODUCTS.map(p => (
              <div key={p.id} onClick={() => setSelectedProduct(p)} style={{ display: 'flex', justifyContent: 'space-between', padding: '12px', border: '1px solid', borderColor: selectedProduct.id === p.id ? '#3b82f6' : '#e5e7eb', borderRadius: '8px', marginBottom: '8px', cursor: 'pointer', backgroundColor: selectedProduct.id === p.id ? '#eff6ff' : 'white' }}>
                <span>{p.name}</span>
                <span style={{ fontWeight: 'bold' }}>{p.price}</span>
              </div>
            ))}
          </div>

          <div style={{ padding: '16px', backgroundColor: '#f9fafb', borderRadius: '8px', marginBottom: '24px' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '12px', color: '#4b5563' }}>
              <CreditCard size={18} />
              <span style={{ fontSize: '14px', fontWeight: '500' }}>Order Summary</span>
            </div>
            <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '14px' }}>
              <span>{selectedProduct.name}</span>
              <span>{selectedProduct.price}</span>
            </div>
            <hr style={{ margin: '12px 0', border: '0', borderTop: '1px solid #e5e7eb' }} />
            <div style={{ display: 'flex', justifyContent: 'space-between', fontWeight: 'bold' }}>
              <span>Total</span>
              <span>{selectedProduct.price}</span>
            </div>
          </div>

          {status === 'error' && (
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', padding: '12px', backgroundColor: '#fef2f2', color: '#991b1b', borderRadius: '6px', marginBottom: '16px', fontSize: '14px' }}>
              <XCircle size={16} />
              <span>{error}</span>
            </div>
          )}

          <button 
            disabled={status === 'processing'}
            onClick={handlePayment}
            style={{ width: '100%', padding: '14px', border: 'none', borderRadius: '8px', backgroundColor: '#1a1a1a', color: 'white', fontSize: '16px', fontWeight: '600', cursor: status === 'processing' ? 'not-allowed' : 'pointer', opacity: status === 'processing' ? 0.7 : 1 }}
          >
            {status === 'processing' ? 'Processing...' : 'Pay Now'}
          </button>
        </>
      )}
    </div>
  );
};

export default App;
