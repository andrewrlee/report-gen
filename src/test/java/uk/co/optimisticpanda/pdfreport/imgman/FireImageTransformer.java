package uk.co.optimisticpanda.pdfreport.imgman;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import uk.co.optimisticpanda.pdfreport.ImageTransformer;

public class FireImageTransformer implements ImageTransformer<FireData> {

	@Override
	public BufferedImage transform(FireData data, BufferedImage image) {
		Point2D.Double centre = getCentrePoint(image);
		Graphics2D g = image.createGraphics();
		int scale = 14; 
		g.setColor(new Color(255, 255, 255, 255));
		
		List<Point2D.Double> arrowPoints = getArrowPoints(centre, scale);
		List<Point2D.Double> inlayPoints = getArrowInlay(arrowPoints, 5);
		
		g.setColor(Color.BLACK);
		g.fillPolygon(getPolygonFromPoints(rotate(arrowPoints, centre, 180)));
		g.setColor(Color.GREEN);
		g.fillPolygon(getPolygonFromPoints(rotate(inlayPoints, centre, 180)));

		Point2D.Double twin = new Point2D.Double(centre.getX(), (centre.getY() + (2 * (scale * 4))));
		Point2D twinPoint = rotate(centre, 180, twin);
		arrowPoints = getArrowPoints(twinPoint, scale);
		inlayPoints = getArrowInlay(arrowPoints, 5);
		
		g.setColor(Color.BLACK);
		g.fillPolygon(getPolygonFromPoints(rotate(arrowPoints, twinPoint, 0)));
		g.setColor(Color.RED);
		g.fillPolygon(getPolygonFromPoints(rotate(inlayPoints, twinPoint, 0)));

		return image;
	}

	private Point2D.Double getCentrePoint(BufferedImage image) {
		int x = image.getWidth() / 2;
		int y = image.getHeight() / 2;
		return new Point2D.Double(x, y);
	}

	private Polygon getPolygonFromPoints(List<Point2D> points) {
		int[] xs = points.stream().mapToInt(p -> (int) p.getX()).toArray();
		int[] ys = points.stream().mapToInt(p -> (int) p.getY()).toArray();
		return new Polygon(xs, ys, points.size());
	}

	private List<Point2D.Double> getArrowInlay(List<Point2D.Double> points, int modifier) {
		List<Point2D.Double> result = new ArrayList<>();
		for (int i = 0; i < points.size(); i++) {
			Point2D.Double point = points.get(i);
			if (i == 0) {
				result.add(new Point2D.Double(point.x - modifier, point.y + modifier));
			}
			if (i == 1) {
				result.add(new Point2D.Double(point.x - modifier, point.y + modifier));
			}
			if (i == 2) {
				result.add(new Point2D.Double(point.x - (2 * modifier), point.y + modifier));
			}
			if (i == 3) {
				result.add(new Point2D.Double(point.x, point.y -  modifier));
			}
			if (i == 4) {
				result.add(new Point2D.Double(point.x + (2 * modifier), point.y + modifier));
			}
			if (i == 5) {
				result.add(new Point2D.Double(point.x + modifier, point.y + modifier));
			}
			if (i == 6) {
				result.add(new Point2D.Double(point.x + modifier, point.y + modifier));
			}
		}
		return result;
	}

	private List<Point2D.Double> getArrowPoints(Point2D centre, double modifier) {
		return Arrays.asList(
				new Point2D.Double(centre.getX() + (modifier * 1), centre.getY() + (modifier * -4)), 
				new Point2D.Double(centre.getX() + (modifier * 1), centre.getY() + (modifier * 2)), 
				new Point2D.Double(centre.getX() + (modifier * 2), centre.getY() + (modifier * 2)), 
				new Point2D.Double(centre.getX() + (modifier * 0), centre.getY() + (modifier * 4)), 
				new Point2D.Double(centre.getX() + (modifier * -2), centre.getY() + (modifier * 2)), 
				new Point2D.Double(centre.getX() + (modifier * -1), centre.getY() + (modifier * 2)), 
				new Point2D.Double(centre.getX() + (modifier * -1), centre.getY() + (modifier * -4)));
	}

	private List<Point2D> rotate(List<Point2D.Double> points, Point2D centre, int degrees) {
		return points.stream().map(p -> rotate(centre, degrees, p)).collect(Collectors.toList());
	}
	
	private Point2D rotate(Point2D centre, int angle, Point2D.Double point) {
		Point2D result = new Point2D.Double();
	    AffineTransform rotation = new AffineTransform();
	    double angleInRadians = Math.toRadians(angle);
	    rotation.rotate(angleInRadians, centre.getX(), centre.getY());
	    return rotation.transform(point, result);
	}
}
