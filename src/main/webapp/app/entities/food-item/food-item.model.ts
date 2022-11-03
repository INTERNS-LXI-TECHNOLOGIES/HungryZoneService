export interface IFoodItem {
  id: number;
  quandity?: number | null;
  unit?: string | null;
}

export type NewFoodItem = Omit<IFoodItem, 'id'> & { id: null };
