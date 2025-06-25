import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { decodeToken, type TokenPayload } from '../utils/jwt';

interface AuthState {
  token:  string | null;
  user:   TokenPayload | null;
  login:  (jwt: string) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      token: null,
      user : null,

      login: (jwt) => {
        const payload = decodeToken(jwt);
        if (!payload) throw new Error('Token inválido');
        set({ token: jwt, user: payload });
      },

      logout: () => set({ token: null, user: null }),
    }),
    { name: 'auth-storage' }        // se guarda en localStorage
  )
);
