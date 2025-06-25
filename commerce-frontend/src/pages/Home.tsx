import { useNavigate } from 'react-router-dom';
import Header        from '../components/Header';
import MerchantTable from '../components/MerchantTable';
import { useAuthStore } from '../store/authStore';
import { descargarCSV } from '../api/comerciantes';

import '../components/styles/Table.css';
import '../components/styles/Buttons.css';

export default function Home() {
  const navigate          = useNavigate();
  const { user }          = useAuthStore();
  const isAdmin = (user?.rol ?? '').toLowerCase() === 'administrador';


  /* descarga reporte */
  const onDownload = async () => {
    try {
      const blob = await descargarCSV();
      const url  = URL.createObjectURL(blob);
      const a    = document.createElement('a');
      a.href = url;
      a.download = 'comerciantes.csv';
      document.body.appendChild(a);
      a.click();
      a.remove();
      URL.revokeObjectURL(url);
    } catch (e: any) {
      alert(e.message);
    }
  };

  return (
    <>
      <Header />

      <main className="contenido">
        {/* ---------- título + línea ---------- */}
        <div className="section-header">
          <h2 className="section-title">Lista Formularios Creados</h2>
          <div className="section-underline" />
        </div>

        {/* ---------- barra de acciones ---------- */}
        <div className="toolbar">
          {isAdmin && (
            <button className="btn-rose" onClick={onDownload}>
              Descargar Reporte en CSV
            </button>
          )}
          <button className="btn-blue" onClick={() => navigate('/merchant/new')}>
            Crear Comerciante
          </button>
        </div>

        {/* ---------- tabla ---------- */}
        <div className="table-wrapper">
          <MerchantTable />
        </div>

       
      </main>
       {/* ---------- franja inferior ---------- */}
        <div className="footer-bar" />
    </>
  );
}
