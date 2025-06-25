import LoginForm from '../components/LoginForm';
import '../components/styles/Login.css';

const Login = () => (
  <div className="login-page">
    {/* 🔻 Título FUERA de la card 🔻 */}
    <h2 className="login-title">Debes iniciar sesión para acceder a la plataforma</h2>

    {/* Card blanca con el formulario */}
    <div className="login-card">
      <small>Digite el correo electronico del propietario o repesentante legal y la contraseña</small>
      <LoginForm />
    </div>
  </div>
);

export default Login;
