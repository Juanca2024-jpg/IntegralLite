// src/router/AppRouter.tsx
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Home         from '../pages/Home';
import Login        from '../pages/Login';
import MerchantForm from '../pages/MerchantForm';
import { useAuthStore } from '../store/authStore';
import type { JSX } from 'react';

const Private = ({ children }: { children: JSX.Element }) =>
  useAuthStore.getState().token ? children : <Navigate to="/login" replace />;

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        {/* pública */}
        <Route path="/login" element={<Login />} />

        {/* privadas */}
        <Route
          path="/home"
          element={<Private><Home /></Private>}
        />

        <Route
          path="/merchant/new"
          element={<Private><MerchantForm mode="create" /></Private>}
        />

        <Route
          path="/merchant/:id/edit"
          element={<Private><MerchantForm mode="edit" /></Private>}
        />

        {/* fallback */}
        <Route path="*" element={<Navigate to="/home" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
