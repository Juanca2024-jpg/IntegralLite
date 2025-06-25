import { useAuthStore } from '../store/authStore';

export async function listarMunicipios(): Promise<string[]> {
  const token = useAuthStore.getState().token;

  const res = await fetch('/api/lov/municipios', {
    headers: { Authorization: `Bearer ${token ?? ''}` }
  });

  if (!res.ok) throw new Error('No se pudieron obtener municipios');
  return res.json();   
}
