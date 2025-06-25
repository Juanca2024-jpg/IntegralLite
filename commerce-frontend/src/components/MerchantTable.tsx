// src/components/MerchantTable.tsx
import { useEffect, useRef, useState } from 'react';
import {
  listarComerciantes,
  eliminarComerciante,
  toggleEstado,
} from '../api/comerciantes';
import { useAuthStore } from '../store/authStore';
import './styles/Table.css';

/* -------- tipos -------- */
interface Comerciante {
  id: number;
  nombreRazonSocial: string;
  telefono: string | null;
  correo: string | null;
  fechaRegistro: string;
  estado: 'A' | 'I';
}

const PAGE_SIZES = [5, 10, 15];
const MAX_BTNS   = 7;           // máx. botones visibles en la barra

export default function MerchantTable() {
  const { user }  = useAuthStore();
  const isAdmin = (user?.rol ?? '').toLowerCase() === 'administrador';


  /* -------- estado -------- */
  const [rows,   setRows]   = useState<Comerciante[]>([]);
  const [page,   setPage]   = useState(0);
  const [size,   setSize]   = useState(5);
  const [ultima, setUltima] = useState(false);
  const [load,   setLoad]   = useState(false);

  const filtrosRef = useRef<{  /* para filtros futuros */
    municipio?: string;
    nombre?:    string;
    estado?:    string;
    fechaRegistro?: string;
  }>({});

  /* -------- cargar datos -------- */
  useEffect(() => {
    const fetchData = async () => {
      setLoad(true);
      try {
        const data = await listarComerciantes(page, size, filtrosRef.current);
        setRows(data);
        setUltima(data.length < size);
      } catch (err: any) {
        console.error(err);
        alert(err.message);
      }
      setLoad(false);
    };
    fetchData();
  }, [page, size]);

  /* -------- acciones -------- */
  const onDelete = async (id: number) => {
    if (!confirm('¿Eliminar definitivamente?')) return;
    await eliminarComerciante(id);
    setPage(0);                         // recarga desde la primera
  };

  const onToggle = async (c: Comerciante) => {
    const next = c.estado === 'A' ? 'I' : 'A';
    await toggleEstado(c.id, next);
    setRows(prev => prev.map(r => (r.id === c.id ? { ...r, estado: next } : r)));
  };

  /* -------- paginador helper -------- */
  const total = ultima ? page + 1 : page + 2;    // estimación sencilla

  const paginasVisibles = (): number[] => {
    const half = Math.floor(MAX_BTNS / 2);
    let ini = Math.max(0, page - half);
    let fin = ini + MAX_BTNS - 1;

    if (fin >= total) {
      fin = total - 1;
      ini = Math.max(0, fin - MAX_BTNS + 1);
    }

    return Array.from({ length: fin - ini + 1 }, (_, i) => i + ini);
  };

  /* -------- render -------- */
  return (
    <>
      <table className="m-table">
        <thead>
          <tr>
            <th>Razón Social</th>
            <th>Teléfono</th>
            <th>Correo Electrónico</th>
            <th>Fecha Registro</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody>
          {load && (
            <tr>
              <td colSpan={6} style={{ textAlign: 'center' }}>Cargando…</td>
            </tr>
          )}

          {!load && rows.length === 0 && (
            <tr>
              <td colSpan={6} style={{ textAlign: 'center' }}>Sin datos</td>
            </tr>
          )}

          {rows.map(c => (
            <tr key={c.id}>
              <td>{c.nombreRazonSocial}</td>
              <td>{c.telefono ?? '-'}</td>
              <td>{c.correo   ?? '-'}</td>
              <td>{c.fechaRegistro}</td>
              <td>
                <span className={`badge badge-${c.estado === 'A' ? 'ok' : 'off'}`}>
                  {c.estado === 'A' ? 'Activo' : 'Inactivo'}
                </span>
              </td>
              <td>
                {/* editar */}
                <button
                  className="action-btn"
                  title="Editar"
                  onClick={() => (location.href = `/merchant/${c.id}/edit`)}
                >✏️</button>

                {/* activar / inactivar */}
                <button
                  className="action-btn"
                  title="Cambiar estado"
                  onClick={() => onToggle(c)}
                >{c.estado === 'A' ? '🚫' : '✅'}</button>

                {/* eliminar (solo admin) */}
                {isAdmin && (
                  <button
                    className="action-btn"
                    title="Eliminar"
                    onClick={() => onDelete(c.id)}
                  >🗑️</button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* ---------- paginador ---------- */}
      <div className="paginator-ctr">
        <label>
          Items:&nbsp;
          <select
            value={size}
            onChange={e => { setSize(+e.target.value); setPage(0); }}
          >
            {PAGE_SIZES.map(n => <option key={n}>{n}</option>)}
          </select>
        </label>

        <ul className="pagination">
          <li>
            <button disabled={page === 0} onClick={() => setPage(p => p - 1)}>
              &lt;
            </button>
          </li>

          {page > MAX_BTNS / 2 && (
            <>
              <li><button onClick={() => setPage(0)}>1</button></li>
              <li className="dots">…</li>
            </>
          )}

          {paginasVisibles().map(n => (
            <li key={n}>
              <button
                className={n === page ? 'active' : ''}
                onClick={() => setPage(n)}
              >
                {n + 1}
              </button>
            </li>
          ))}

          {!ultima && (
            <>
              <li className="dots">…</li>
              <li>
                <button onClick={() => setPage(total - 1)}>
                  {total}
                </button>
              </li>
            </>
          )}

          <li>
            <button disabled={ultima} onClick={() => setPage(p => p + 1)}>
              &gt;
            </button>
          </li>
        </ul>
      </div>
    </>
  );
}
