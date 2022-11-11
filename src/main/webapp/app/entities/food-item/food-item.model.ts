export interface IFoodItem {
  id: number;
  quantity?: number | null;
  unit?: string | null;
}

export type NewFoodItem = Omit<IFoodItem, 'id'> & { id: null };
