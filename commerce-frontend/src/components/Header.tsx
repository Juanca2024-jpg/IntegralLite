import { Link } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';
import logo from '../assets/logo.svg';
import './styles/Header.css';

export default function Header() {
  const { user, logout } = useAuthStore();

  return (
    <header className="topbar">
      {/* ---------- logo ---------- */}
      <Link to="/" className="brand">
        <img src={logo} alt="OLSoftware" className="logo" />
      </Link>

      {/* ---------- enlaces ---------- */}
      <nav className="links">
        <Link to="/home">Lista Formulario</Link>
        <span className="divider" />
        <Link to="/merchant/new">Crear Formulario</Link>
        <Link to="#">Beneficios por renovar</Link>
      </nav>

      {/* ---------- bloque usuario ---------- */}
      {user && (
        <div className="user">
          <div className="avatar" />
          <div className="info">
            <span className="welcome">¡Bienvenido!</span>
            <span className="email">{user.sub}</span>     {/* ← e-mail */}
            <span className="role">{user.rol}</span>
          </div>

          <button className="logout" onClick={logout}>
            Cerrar sesión
          </button>
        </div>
      )}
    </header>
  );
}
