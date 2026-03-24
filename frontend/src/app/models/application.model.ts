export interface Application {
  idApplication: number;
  freelancerName: string;
  projectTitle: string;
  proposal: string;
  hourlyRate: number;
  estimatedDuration: number;
  status: string;
  applicationDate: string;
}

export interface ApplicationRequest {
  proposal: string;
  hourlyRate?: number;
  estimatedDuration: number;
}
