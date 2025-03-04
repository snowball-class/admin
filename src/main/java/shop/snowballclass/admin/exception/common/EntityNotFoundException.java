package shop.snowballclass.admin.exception.common;

import shop.snowballclass.admin.exception.ErrorCode;

public class EntityNotFoundException extends ServiceException {
    public EntityNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public EntityNotFoundException(String message) {
        super(ErrorCode.ENTITY_NOT_FOUND, message);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
