export interface ICategory {
  id: number;
  name?: string | null;
  imageUrl?: string | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
