package com.CRamirezEvaluacionContratosPetroleo.JPA;

import java.util.List; // Necesario para el constructor successList y el campo 'objects'

public class Result<T> { // ¡CAMBIO CLAVE AQUÍ! Hacemos la clase genérica con <T>

    public boolean correct;
    public String errorMessage;
    public Exception ex; // Mantengo tu campo 'ex'
    public T object; // ¡Ahora de tipo T!
    public List<T> objects; // ¡Ahora de tipo List<T>!

    public Result() {
        this.correct = false; // Por defecto, el resultado es incorrecto
    }

    // Constructor para resultados exitosos con un solo objeto
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.correct = true;
        result.object = data;
        return result;
    }

    // Constructor para resultados exitosos con una lista de objetos
    // Este constructor es útil si tu DAO devuelve directamente List<Transaccion>
    // y quieres envolverla en un Result.
    public static <T> Result<T> successList(List<T> dataList) { // Nota: el tipo de Result sigue siendo <T>
        Result<T> result = new Result<>();
        result.correct = true;
        result.objects = dataList; // Asigna la lista al campo 'objects'
        return result;
    }

    // Constructor para resultados de error
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.correct = false;
        result.errorMessage = message;
        return result;
    }

    // Constructor para resultados de error con excepción
    public static <T> Result<T> error(String message, Exception exception) {
        Result<T> result = new Result<>();
        result.correct = false;
        result.errorMessage = message;
        result.ex = exception;
        return result;
    }

    // Getters y Setters (asegúrate de que existan si no usas Lombok para @Data)
    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }
}
