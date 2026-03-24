export interface Review {
  idReview: number;
  evaluatorName: string;
  evaluatorType: string;
  score: number;
  comment: string;
  reviewDate: string;
  projectTitle: string;
}

export interface ReviewRequest {
  score: number;
  comment?: string;
}
