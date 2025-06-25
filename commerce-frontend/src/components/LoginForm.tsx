// src/pages/LoginForm.tsx
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';
import './styles/Login.css';

export default function LoginForm() {
  /* -------- estado del formulario -------- */
  const [correo,   setCorreo]   = useState('');
  const [password, setPassword] = useState('');
  const [acepta,   setAcepta]   = useState(false);
  const [error,    setError]    = useState('');

  const login    = useAuthStore(state => state.login);   // ← único setter
  const navigate = useNavigate();

  /* -------- submit -------- */
  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (!correo || !password)   return setError('Campos obligatorios');
    if (!acepta)                return setError('Debes aceptar los términos');

    try {
      const resp = await fetch('http://localhost:8080/api/auth/login', {
        method : 'POST',
        headers: { 'Content-Type': 'application/json' },
        body   : JSON.stringify({ correo, password })
      });

      if (!resp.ok) {
        return setError(resp.status === 401
          ? 'Credenciales inválidas'
          : 'Error al autenticar');
      }

      // tu backend responde { accessToken: '...' }  (ajusta si es “token”)
      const { token } = await resp.json();

      login(token);          // guarda token + user (ya decodificado)
      navigate('/home');

    } catch {
        console.log("el error es: " + error)
      setError('Error de red — intenta de nuevo');
    }
  };

  /* -------- render -------- */
  return (
    <form onSubmit={submit} className="login-form">
      <div className="form-group">
        <label>Correo electrónico</label>
        <input
          type="email"
          value={correo}
          onChange={e => setCorreo(e.target.value)}
        />
      </div>

      <div className="form-group">
        <label>Contraseña</label>
        <input
          type="password"
          value={password}
          onChange={e => setPassword(e.target.value)}
        />
      </div>

      <label className="checkbox">
        <input
          type="checkbox"
          checked={acepta}
          onChange={e => setAcepta(e.target.checked)}
        />
        Acepto términos y condiciones
      </label>

      {error && <p className="error-msg">{error}</p>}

      <button className="btn-primary" type="submit">
        Iniciar sesión
      </button>
    </form>
  );
}
