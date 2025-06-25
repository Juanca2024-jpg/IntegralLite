
export interface TokenPayload {
  sub:   string; 
  rol:   string;
  iat:   number;
  exp:   number;
}

export function decodeToken(token: string): TokenPayload | null {
  try {
    const [, payload] = token.split('.');
    return JSON.parse(atob(payload)) as TokenPayload;
  } catch {
    return null;          
  }
}
