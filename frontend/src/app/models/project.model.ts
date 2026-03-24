export interface Project {
  idProject: number;
  title: string;
  budgetMin: number;
  budgetMax: number;
  paymentType: string;
  status: string;
  postingDate: string;
  clientName: string;
}

export interface ProjectRequest {
  title: string;
  budgetMin: number;
  budgetMax: number;
  paymentType: string;
}
