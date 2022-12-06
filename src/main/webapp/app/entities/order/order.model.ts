import dayjs from 'dayjs/esm';
import { IFoodItem } from 'app/entities/food-item/food-item.model';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface IOrder {
  id: number;
  orderDate?: dayjs.Dayjs | null;
  quantity?: number | null;
  unit?: string | null;
  orderStatus?: string | null;
  food?: Pick<IFoodItem, 'id'> | null;
  recipient?: Pick<IUserExtra, 'id'> | null;
}

export type NewOrder = Omit<IOrder, 'id'> & { id: null };
