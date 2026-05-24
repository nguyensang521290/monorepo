import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/order-service': {
        target: 'http://localhost:8000',
        changeOrigin: true,
      },
      '/identity-service': {
        target: 'http://localhost:8000',
        changeOrigin: true,
      },
      '/account-service': {
        target: 'http://localhost:8000',
        changeOrigin: true,
      },
      '/payment-service': {
        target: 'http://localhost:8000',
        changeOrigin: true,
      },
    },
  },
});

