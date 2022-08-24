import dayjs from 'dayjs/esm';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { IFood } from 'app/entities/food/food.model';

export interface IOrder {
  id: number;
  orderDate?: dayjs.Dayjs | null;
  quantity?: number | null;
  orderStatus?: string | null;
  donor?: Pick<IUserExtra, 'id'> | null;
  recipient?: Pick<IUserExtra, 'id'> | null;
  foods?: Pick<IFood, 'id'>[] | null;
}

export type NewOrder = Omit<IOrder, 'id'> & { id: null };
