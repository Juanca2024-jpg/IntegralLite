import { useAuthStore } from '../store/authStore';

const BASE = '/api';                      // proxy de Vite se encarga del host

const headers = () => ({
  'Content-Type': 'application/json',
  Authorization: `Bearer ${useAuthStore.getState().token}`
});

export async function authFetch(
  input: RequestInfo,
  init: RequestInit = {}
) {
  const { token, logout } = useAuthStore.getState();

  // encabezados
  const headers: Record<string, string> = {
    ...(init.headers as any),
    Authorization: `Bearer ${token ?? ''}`
  };

  const res = await fetch(input, { ...init, headers });

  if (res.status === 401) {
    logout();
    location.href = '/login';
    throw new Error('Sesión expirada');
  }

  return res;
}

/**  Listar con paginado + filtros  */
export async function listarComerciantes(
  page = 0,
  size = 5,
  filtros: {
    municipio?: string;
    nombre?: string;
    estado?: string;
    fechaRegistro?: string;   // ISO yyyy-MM-dd
  } = {}
) {
  const params = new URLSearchParams({
    page: String(page),
    size: String(size),
    ...filtros                       // solo añade los que no sean undefined
  });

  const res = await fetch(`${BASE}/comerciantes?${params.toString()}`, {
    headers: headers()
  });

  if (!res.ok) throw new Error('Error al listar');

  //  <-- arreglo plano                                  
  return res.json();       
}


export async function eliminarComerciante(id: number) {
  const res = await authFetch(`/api/comerciantes/${id}`, {
    method: 'DELETE'
  });

  if (!res.ok) throw new Error('No se pudo eliminar');
}

export async function toggleEstado(id: number, nuevo: 'A' | 'I') {
  const res = await authFetch(`/api/comerciantes/${id}/estado`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ estado: nuevo })
  });

  if (!res.ok) throw new Error('No se pudo cambiar estado');
  return res.json();           // devuelve el comerciante actualizado
}


/* ---------- Reto 8 ---------- */
export async function descargarCSV(): Promise<Blob> {
  const res = await authFetch('/api/reportes/comerciantes.csv', {
    method: 'GET'
  });
  if (!res.ok) throw new Error('No se pudo descargar el reporte');

  //   ─▶  Pipe-separated text ⇒ lo convertimos a Blob (tipo text/csv)
  const texto = await res.text();
  return new Blob([texto], { type: 'text/csv;charset=utf-8' });
}

export async function crearComerciante(dto: any) {
  const body = JSON.stringify({ ...dto, estado: 'A' });   // 👈
  const res  = await authFetch('/api/comerciantes', {
    method : 'POST',
    headers: { 'Content-Type': 'application/json' },
    body
  });
  if (!res.ok) throw new Error('No se pudo crear');
  return res.json();
}

export async function actualizarComerciante(id: number, dto: any) {
  const body = JSON.stringify({
    ...dto,
    estado: dto.estado ?? 'A'   // si no lo tocamos, envía el mismo valor
  });

  const res = await authFetch(`/api/comerciantes/${id}`, {
    method : 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body
  });
  if (!res.ok) throw new Error('No se pudo actualizar');
  return res.json();
}

export async function consultarComerciante(id: number) {
  const res = await authFetch(`/api/comerciantes/${id}`);
  if (!res.ok) throw new Error('No encontrado');
  return res.json();
}

export async function listarMunicipios(): Promise<string[]> {
  const res = await authFetch('/api/lov/municipios');
  if (!res.ok) throw new Error('Error al listar municipios');
  return res.json();                 // ["Barranquilla","Bogotá", ...]
}

/* ---------- CRUD comerciante (lectura) ---------- */
export async function obtenerComerciante(id: number) {
  const res = await authFetch(`/api/comerciantes/${id}`);
  if (!res.ok) throw new Error('No encontrado');
  return res.json();                 // ← objeto comerciante
}

export async function resumenComerciante(id: number): Promise<{
  totalIngresos:  number;   // BIGDECIMAL → number
  totalEmpleados: number;
}> {
  const res = await authFetch(`/api/comerciantes/${id}/resumen`);
  if (!res.ok) throw new Error('No se pudo obtener el resumen');
  return res.json();
}