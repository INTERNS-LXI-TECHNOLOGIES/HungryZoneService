import { IFood } from 'app/entities/food/food.model';
import { ICart } from 'app/entities/cart/cart.model';

export interface ICartItem {
  id: number;
  quantity?: number | null;
  food?: Pick<IFood, 'id'> | null;
  cart?: Pick<ICart, 'id'> | null;
}

export type NewCartItem = Omit<ICartItem, 'id'> & { id: null };
