import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Header from "../components/Header";
import {
  listarMunicipios,
  crearComerciante,
  actualizarComerciante,
  consultarComerciante,
  resumenComerciante
} from "../api/comerciantes";

import "../components/styles/Form.css";
import "../components/styles/SummaryBar.css";

interface Props { mode: "create" | "edit"; }

interface FormData {
  nombreRazonSocial: string;
  municipio: string;
  telefono?: string;
  correo?: string;
  fechaRegistro: string;
  poseeEstablecimientos: boolean;
  estado?: "A" | "I";
}

export default function MerchantForm({ mode }: Props) {
  const { id } = useParams();
  const navigate = useNavigate();

  /* ───────── STATE ───────── */
  const [munis, setMunis] = useState<string[]>([]);
  const [form, setForm] = useState<FormData>({
    nombreRazonSocial: "",
    municipio: "",
    telefono: "",
    correo: "",
    fechaRegistro: new Date().toISOString().slice(0, 10),
    poseeEstablecimientos: false,
    estado: "A"
  });
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [resume, setResume] = useState<null | {
    totalIngresos: number;
    totalEmpleados: number;
  }>(null);

  /* ───────── LOAD MUNICIPIOS ───────── */
  useEffect(() => { listarMunicipios().then(setMunis); }, []);

  /* ───────── LOAD COMERCIANTE & RESUMEN (edit) ───────── */
  useEffect(() => {
    if (mode === "edit" && id) {
      (async () => {
        try {
          const c = await consultarComerciante(+id);
          setForm({
            nombreRazonSocial: c.nombreRazonSocial,
            municipio: c.municipio,
            telefono: c.telefono ?? "",
            correo: c.correo ?? "",
            fechaRegistro: c.fechaRegistro,
            poseeEstablecimientos: false,
            estado: c.estado
          });
          setResume(await resumenComerciante(+id));
        } catch {
          setError("Error al cargar el comerciante");
        }
      })();
    }
  }, [mode, id]);

  /* ───────── HANDLERS ───────── */
  const handle = (k: keyof FormData) => (e: any) =>
    setForm({
      ...form,
      [k]: k === "poseeEstablecimientos" ? e.target.checked : e.target.value
    });

  /* ───────── SUBMIT ───────── */
  const enviar = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);
    try {
      if (mode === "create") {
        await crearComerciante({ ...form, estado: "A" });
      } else if (id) {
        await actualizarComerciante(+id, form); // mantiene su estado
      }
      navigate("/home");
    } catch {
      setError("Ocurrió un error al guardar");
    }
    setSaving(false);
  };

  /* ───────── UI ───────── */
  return (
    <>
      <Header />

      <div className="merchant-wrapper">
        <h2>{mode === "create" ? "Nuevo Comerciante" : "Editar Comerciante"}</h2>

        <div className="merchant-card">
          <form onSubmit={enviar}>
            <div className="grid-2col">
              <label>
                Razón Social *
                <input
                  required
                  value={form.nombreRazonSocial}
                  onChange={handle("nombreRazonSocial")}
                />
              </label>

              <label>
                Correo electrónico
                <input
                  type="email"
                  value={form.correo}
                  onChange={handle("correo")}
                />
              </label>

              <label>
                Municipio / Ciudad *
                <select
                  required
                  value={form.municipio}
                  onChange={handle("municipio")}
                >
                  <option value="">-- Seleccione --</option>
                  {munis.map((m) => (
                    <option key={m}>{m}</option>
                  ))}
                </select>
              </label>

              <label>
                Fecha de registro *
                <input
                  type="date"
                  required
                  value={form.fechaRegistro}
                  onChange={handle("fechaRegistro")}
                />
              </label>

              <label>
                Teléfono
                <input value={form.telefono} onChange={handle("telefono")} />
              </label>

              <label className="checkbox-row">
                <input
                  type="checkbox"
                  checked={form.poseeEstablecimientos}
                  onChange={handle("poseeEstablecimientos")}
                />
                Posee establecimientos
              </label>
            </div>

            {error && <p className="error">{error}</p>}

            <button className="btn-blue" disabled={saving}>
              {saving ? "Guardando…" : "Enviar formulario"}
            </button>
          </form>
        </div>
      </div>

      {/* ───────── BARRA RESUMEN (edit) ───────── */}
      {mode === "edit" && resume && (
        <div className="summary-bar">
          <div className="summary-item">
            <span>Total Ingresos formulario:</span>
            <span>
              $
              {resume.totalIngresos.toLocaleString("es-CO", {
                minimumFractionDigits: 2
              })}
            </span>
          </div>
          <div className="summary-item">
            <span>Cantidad de empleados:</span>
            <span>{resume.totalEmpleados}</span>
          </div>
          <div />
        </div>
      )}
    </>
  );
}
