import dayjs from 'dayjs/esm';
import { IFoodItem } from 'app/entities/food-item/food-item.model';
import { ICategory } from 'app/entities/category/category.model';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface IFood {
  id: number;
  name?: string | null;
  expiry?: dayjs.Dayjs | null;
  description?: string | null;
  imageUrl?: string | null;
  food?: Pick<IFoodItem, 'id'> | null;
  category?: Pick<ICategory, 'id'> | null;
  donor?: Pick<IUserExtra, 'id'> | null;
}

export type NewFood = Omit<IFood, 'id'> & { id: null };
