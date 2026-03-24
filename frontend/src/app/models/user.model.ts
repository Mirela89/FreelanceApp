export interface AuthResponse {
  token: string;
  email: string;
  role: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  role: string;
  profileTitle?: string;
  clientType?: string;
  companyName?: string;
  taxId?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}
